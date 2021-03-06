/**
 * GameFX
 * <p>
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 Tarun Boddupalli
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.tarunb.gamefx.view;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewManager {

    private static Map<String, View> registeredViews = new HashMap<>();

    private static View defaultView = new View();
    private static View currentView = null;

    public static void setView(String id, Stage stage) {
        View newView = getView(id);
        if (newView == null) {
            throw new NullPointerException("View with id " + id + " doesn't exist");
        }

        if (currentView != null) {
            currentView.onBeforeUnload();
        }
        newView.onBeforeLoad();
        stage.setScene(newView);
        newView.onAfterLoad();
        if (currentView != null) {
            currentView.onAfterUnload();
        }
        currentView = newView;
    }

    public static void registerView(View view) {
        registeredViews.put(view.getId(), view);
    }

    public static View getDefaultView() {
        return defaultView;
    }

    public static void setDefaultView(String defaultViewId) {
        ViewManager.defaultView = getView(defaultViewId);
    }

    public static View getView(String id) {
        return registeredViews.get(id);
    }

}
