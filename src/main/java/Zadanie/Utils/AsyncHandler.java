package Zadanie.Utils;

import javax.swing.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AsyncHandler<T> extends SwingWorker<T, Void> {
    private Supplier<T> backgroundHandler;
    private Consumer<T> edtHandler;


    public AsyncHandler(Supplier<T> backgroundHandler, Consumer<T> edtHandler) {
        this.backgroundHandler = backgroundHandler;
        this.edtHandler = edtHandler;
    }

    @Override
    protected T doInBackground() throws Exception {
        return backgroundHandler.get();
    }

    @Override
    protected void done() {
        try {
            edtHandler.accept(get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
