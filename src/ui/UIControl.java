package ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Works like the <code>@FXML</code> annotation but instead of using it with FXML documents,
 * it is usable for creating JavaFX elements in code and linking those elements to a controller class with
 * fields that mirror the elements.
 * 
 * <h1>How to use the <code>@UIControl</code> annotation</h1>
 * 
 * You need two classes:
 * <blockquote>
 * <li>a class the implements <code>UIController</code></li>
 * <li>a class the implements <code>UIContent</code></li>
 * </blockquote>
 * The content class contains all JavaFX elements and handles the initialization and structure.
 * The controller class contains uninitialized references to those elements just like a normal
 * FXML controller.
 * But this time you do not use <code>@FXML</code> but <code>@UIControl</code> instead.
 * You need to annotate the fields both in the controller and in the content class with <code>UIControl</code>.
 * Make sure they both have the same type and the same name.
 * Register both classes by calling this function:
 * <br><br>
 * {@link UILoader#registerUIComponent(String, UIContent, Class, ui.UILoader.LoadingBehavior)}
 * <br><br>
 * After loading everything with the <code>UILoader</code>, the controller instance's fields will be
 * initialized with references to the fields of the UIContent instance.
 * 
 * @author Andreas Wegner
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UIControl
{
	
}
