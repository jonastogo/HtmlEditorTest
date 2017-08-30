import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;

/**
 * This class stores the properties used to identify an {@link Element}. These properties define the
 * {@link HTML.Tag} the {@link HTML.Attribute} and the value of the HTML.Attribute.<br>
 * Example: Find a HTML element with the HTML tag "TD" the HTML attribute "comment" and the comment
 * value LIST_DATA. The CompareCondition has to be instantiated like this.<br>
 * <code>new CompareCondition(HTML.Tag.TD, HTML.Attribute.COMMENT, HtmlContentType.LIST_DATA.toString())</code>
 * This compare condition is used in {@link ElementUtils}
 *
 * @author Rüdiger Gründel
 *
 */
public class CompareCondition {
    /**
     * Attribute name
     */
    private Object attrName;

    /**
     * Attribute value
     */
    private Object attrValue;

    /**
     * Compare object
     */
    private HTML.Tag compareTag;

    /**
     * Constructs a CompareCondition with the given parameter.
     *
     * @param compareTag
     *            the tag of the element to identify.
     * @param attrName
     *            the attribute name of the element to identify.
     * @param attrValue
     *            the attribute value of the element to identify.
     */
    public CompareCondition(final Tag compareTag,
            final Object attrName,
            final Object attrValue) {

        this.compareTag = compareTag;
        this.attrName = attrName;
        this.attrValue = attrValue;

    }

    /**
     * Constructs a CompareCondition with the given parameter.
     *
     * @param compareTag
     *            the tag of the element to identify.
     * @param attrName
     *            the attribute name of the element to identify.
     */
    public CompareCondition(final Tag compareTag,
            final Object attrName) {
        this(compareTag, attrName, null);
    }

    /**
     * Constructs a CompareCondition with the given parameter.
     *
     * @param compareTag
     *            the tag of the element to identify.
     */
    public CompareCondition(final Tag compareTag) {
        this(compareTag, null, null);
    }

    /**
     * Returns the name of the attribute.
     *
     * @return the name of the attribute.
     */
    public Object getAttrName() {
        return attrName;
    }

    /**
     * Sets the name of the attribute.
     *
     * @param attrName
     *            the name of the attribute.
     */
    public void setAttrName(final Object attrName) {
        this.attrName = attrName;
    }

    /**
     * Returns the value of the attribute.
     *
     * @return the value of the attribute.
     */
    public Object getAttrValue() {
        return attrValue;
    }

    /**
     * Sets the value of the attribute.
     *
     * @param attrValue
     *            the the value of the attribute.
     */
    public void setAttrValue(final Object attrValue) {
        this.attrValue = attrValue;
    }

    /**
     * Returns the {@link HTML.Tag} of the element.
     *
     * @return the {@link HTML.Tag} of the element.
     */
    public HTML.Tag getCompareTag() {
        return compareTag;
    }

    /**
     * Sets the {@link HTML.Tag} of the element.
     *
     * @param compareTag
     *            the {@link HTML.Tag} of the element.
     */
    public void setCompareTag(final HTML.Tag compareTag) {
        this.compareTag = compareTag;
    }

    @Override
    public String toString() {
        return new StringBuilder("Name= " + attrName).append("; Value= " + attrValue).append("; CompareTag= " + compareTag).toString();

    }

}
