package com.flipkart.layoutengine.parser;

import android.content.Context;
import android.view.View;

import com.flipkart.layoutengine.ParserContext;
import com.flipkart.layoutengine.view.ProteusView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author kirankumar
 */
public class WrappableParser<T extends View> extends Parser<T> {

    private final Parser<T> wrappedParser;

    public WrappableParser(Class viewClass, Parser<T> wrappedParser) {
        super(viewClass);
        this.wrappedParser = wrappedParser;
    }

    @Override
    protected void prepareHandlers(Context context) {
        if (wrappedParser != null) {
            wrappedParser.prepareHandlers(context);
        }
    }

    @Override
    public boolean handleAttribute(ParserContext context, String attribute, JsonElement element, JsonObject layout,
                                   ProteusView view, int childIndex) {
        boolean handled = super.handleAttribute(context, attribute, element, layout, view, childIndex);
        if (wrappedParser != null && !handled) {
            handled = wrappedParser.handleAttribute(context, attribute, element, layout, view, childIndex);
        }
        return handled;
    }
}
