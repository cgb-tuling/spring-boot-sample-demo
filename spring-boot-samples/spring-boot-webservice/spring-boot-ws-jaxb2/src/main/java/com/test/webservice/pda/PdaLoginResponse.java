//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2022.05.23 时间 04:25:49 PM CST 
//


package com.test.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request-result" type="{http://test.com/webservice/pda}LoginRequestResult"/>
 *         &lt;element name="menu-result" type="{http://test.com/webservice/pda}MenuList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "requestResult",
    "menuResult"
})
@XmlRootElement(name = "PdaLoginResponse")
public class PdaLoginResponse {

    @XmlElement(name = "request-result", required = true)
    protected LoginRequestResult requestResult;
    @XmlElement(name = "menu-result", required = true)
    protected MenuList menuResult;

    /**
     * 获取requestResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link LoginRequestResult }
     *     
     */
    public LoginRequestResult getRequestResult() {
        return requestResult;
    }

    /**
     * 设置requestResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link LoginRequestResult }
     *     
     */
    public void setRequestResult(LoginRequestResult value) {
        this.requestResult = value;
    }

    /**
     * 获取menuResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MenuList }
     *     
     */
    public MenuList getMenuResult() {
        return menuResult;
    }

    /**
     * 设置menuResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MenuList }
     *     
     */
    public void setMenuResult(MenuList value) {
        this.menuResult = value;
    }

}
