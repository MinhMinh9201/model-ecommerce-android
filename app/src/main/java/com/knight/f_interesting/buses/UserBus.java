package com.knight.f_interesting.buses;

import com.knight.f_interesting.models.User;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class UserBus {
    private static final BehaviorSubject<User> behaviorSubject
            = BehaviorSubject.create();
    public static Disposable subscribe(@NonNull Consumer<User> action) {
        return behaviorSubject.subscribe(action);
    }
    public static User current(){
        return behaviorSubject.getValue();
    }
    public static void publish(User user){
        behaviorSubject.onNext(user);
    }
}
