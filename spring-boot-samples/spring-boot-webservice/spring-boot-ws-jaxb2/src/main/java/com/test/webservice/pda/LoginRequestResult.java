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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>LoginRequestResult complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="LoginRequestResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request_result" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="result_desc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginRequestResult", propOrder = {
    "requestResult",
    "resultDesc"
})
public class LoginRequestResult {

    @XmlElement(name = "request_result")
    protected boolean requestResult;
    @XmlElement(name = "result_desc", required = true)
    protected String resultDesc;

    /**
     * 获取requestResult属性的值。
     * 
     */
    public boolean isRequestResult() {
        return requestResult;
    }

    /**
     * 设置requestResult属性的值。
     * 
     */
    public void setRequestResult(boolean value) {
        this.requestResult = value;
    }

    /**
     * 获取resultDesc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     * 设置resultDesc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultDesc(String value) {
        this.resultDesc = value;
    }

}
