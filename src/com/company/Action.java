package com.company;

public class Action<A> {

    public final Unit unit = new Unit();

        Function<Unit,A> func;

        public threaded_action get_threaded_action(){
            return  new threaded_action(this);
        }

        public Action(A val){
            this.func = new Function<Unit, A>() {
                @Override
                public A func(Unit input) {
                    return val;
                }
            };
        }

        public static<A> Action<A> pure(A val){
            return new Action<>(val);
        }


        public Action(Function<Unit,A> f){
            this.func = f;
        }

        public static <A,B> Action<B> bind(Action<A> x,Function<A,Action<B>> f){

            return new Action<>(new Function<Unit, B>() {
                @Override
                public B func(Unit input) {
                    return f.func(x.get_threaded_action().result).get_threaded_action().result;
                }
            });
        }


    public static class Unit{
        public Unit(){

        }
    }

    public class threaded_action implements Runnable{
            Action<A> action;
            A result;
            public threaded_action(Action<A> action){
                this.action = action;
            }

            public A eval(){
                Thread thread = new Thread(this);
                thread.start();
                while (thread.isAlive()){}
                return this.result;
            }

            public void _eval(){
                Thread thread = new Thread(this);
                thread.start();
            }

            public void run(){
                result = this.action.func.func(unit);
            }
    }



}
