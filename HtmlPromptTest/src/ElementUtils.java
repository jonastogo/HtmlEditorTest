import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument;


public class ElementUtils {

    /**
    *
    * @param doc
    * @param tag
    * @return
    */
   public static final Element findElementRootMatchingTag(final HTMLDocument doc,
           final HTML.Tag tag) {

       final Element root = doc.getRootElements()[0];

       for (int i = 0; i < root.getElementCount(); i++) {
           final Element element = root.getElement(i);
           if (element.getAttributes().getAttribute(StyleConstants.NameAttribute) == tag) {
               return element;
           }
       }
       return null;
   }

   /**
    *
    * @param htmlDoc
    * @return
    */
   public static Element getBodyElement(final HTMLDocument htmlDoc) {
       Element element;
       element = findElementRootMatchingTag(htmlDoc, HTML.Tag.BODY);
       return element;
   }
   
   /**
    * Returns the last command line DIV element
    *
    *
    * @param document
    *
    * @return
    */
   public static final Element getLastCommandLine(final HTMLDocument document) {
       final Element root = document.getRootElements()[0];
       final Element lastElem = root.getElement(root.getElementCount() - 1);
       final int count = lastElem.getElementCount();

       for (int i = count - 1; i >= 0; i--) {
           Element child = lastElem.getElement(i);
           if (child != null) {
               child = checkForTagAndAttribute(child, new CompareCondition(HTML.Tag.DIV, HTML.Attribute.TYPE, "COMMAND_LINE"));
               if (child != null) {
                   return child;
               }
           }
       }
       return null;
   }
   
   /**
    * This method checks if the HTML tag compares to the given compare object from the
    * <code>CompareCondition</code>.
    *
    * @param element
    * @param compare
    * @return
    */
   public static final Element checkForTagAndAttribute(final Element element,
           final CompareCondition compare) {
       Element result = null;
       final AttributeSet aSet = element.getAttributes();

       if (findTag(element, compare.getCompareTag()) != null) {
           if (compare.getAttrValue() != null) {
               if (aSet.containsAttribute(compare.getAttrName(), compare.getAttrValue())) {
                   result = element;
               }
           } else {
               final Enumeration<?> enumNames = aSet.getAttributeNames();
               while (enumNames.hasMoreElements()) {
                   final Object name = enumNames.nextElement();
                   if (name instanceof Attribute) {
                       final Attribute attName = (Attribute) name;
                       if (attName.equals(compare.getAttrName())) {
                           result = element;
                           break;
                       }
                   }
               }
           }
       }

       return result;
   }
   
   /**
    * This method proves if the given <code>Element</code> equals the given <code>Tag</code>.
    *
    * @param element
    *            the element to compare.
    * @param tag
    *            the tag to compare.
    * @return returns true if the given element is the given {@link HTML.Tag}. Otherwise false.
    */
   public static final Element findTag(final Element element,
           final Tag tag) {
       final AttributeSet aSet = element.getAttributes();
       final Object o = aSet.getAttribute(StyleConstants.NameAttribute);
       final HTML.Tag kind = (HTML.Tag) o;
       if (kind.equals(tag)) {
           return element;
       }
       return null;
   }

}
