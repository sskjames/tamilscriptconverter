package org.tamilscriptconverter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author Arunachalam
 */
public class SpecialSoundChar implements Serializable
{
    private String keyChar;
    private String previousChar;
    private String valueChar;

    public SpecialSoundChar()
    {
    }

    public SpecialSoundChar(String keyChar, String previousChar, String valueChar)
    {
        this.keyChar = keyChar;
        this.previousChar = previousChar;
        this.valueChar = valueChar;
    }

    public String getKeyChar()
    {
        return keyChar;
    }

    public void setKeyChar(String keyChar)
    {
        this.keyChar = keyChar;
    }

    public String getPreviousChar()
    {
        return previousChar;
    }

    public void setPreviousChar(String previousChar)
    {
        this.previousChar = previousChar;
    }

    public String getValueChar()
    {
        return valueChar;
    }

    public void setValueChar(String valueChar)
    {
        this.valueChar = valueChar;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof SpecialSoundChar) {
            return EqualsBuilder.reflectionEquals(this, object);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
