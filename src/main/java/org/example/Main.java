package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.stream.IntStream;

public class Main {

    Observable<Integer> createObservableExplicita(){
        return Observable.create(subscriber -> {
            try {
                IntStream.range(1,10)
                    .forEach(subscriber::onNext);
                subscriber.onComplete();
            }catch (Throwable cause){
                subscriber.onError(cause);
            }
        });
    };

    void metodosObservadorLargo(){
        Observable.just(1, 3, 5, 4)
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    System.out.println("suscrito");
                }

                @Override
                public void onNext(@NonNull Integer integer) {
                    System.out.println("onNext :" + integer);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    System.out.println("error");
                }

                @Override
                public void onComplete() {
                    System.out.println("complete");
                }
            });
    }

    void metodoObservadorLambda(){
        Observable.just(1, 3, 5, 4)
            .subscribe(
                integer -> System.out.println("Valor recibido: " + integer),
                throwable -> System.out.println("Valor recibido: " + throwable.getMessage()),
                () -> System.out.println("No mas elementos"));
    }

    void metodosObservable(){
        Observable.just(1, 3, 5, 4)
            .doOnNext(v -> {
                if (v == 4){
                    throw new RuntimeException("El cuatro es feo");
                }
            })
            .doOnError( throwable -> System.out.println("onError: " + throwable.getMessage()))
            .doOnComplete(() -> System.out.println("Completable"))
            .doOnSubscribe(disposable -> System.out.println("Subscribe"))
            .subscribe(System.out::println);
    }

    void error(){
        Observable.error(Throwable::new)
            .doOnError(throwable -> System.out.println("Error"))
            .subscribe();
    }

    void operadores() {
        Observable.just(1, 3, 5, 4)
            .filter(x -> x > 2)
            .sorted((o1, o2) -> o2.compareTo(o1))
            .take(2)
            .map(String::valueOf)
            .subscribe(System.out::println);
    }

    void operadoresCombinacion(){
        Observable<Integer> ob01 =  Observable.just(1,2,3,4);
        Observable<Integer> ob02 =  Observable.just(5,6,7,8);

        Observable.merge(ob01, ob02).subscribe(System.out::println);
    }

    public static void main(String[] args) {
        Main app = new Main();
        //app.operadores();
        //app.metodosObservadorLargo();
        //app.metodoObservadorLambda();
        //app.metodosObservable();
        //app.error();
        app.operadoresCombinacion();

//        app.createObservableExplicita()
//            .doOnNext(integer -> System.out.println("value: "+integer))
//            .doOnComplete(() -> System.out.println("completable"))
//            .doOnError(throwable -> System.out.println("error: " + throwable.getMessage()))
//            .subscribe();

    }
}