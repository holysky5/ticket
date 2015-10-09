/**
 * GetmesxmlLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ourlife.dev.terminal.bz;

import com.ourlife.dev.common.config.Global;

public class GetmesxmlLocator extends org.apache.axis.client.Service implements
		Getmesxml {

	public GetmesxmlLocator() {
	}

	public GetmesxmlLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public GetmesxmlLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for getmesxmlPort
	private java.lang.String getmesxmlPort_address = Global
			.getConfig("bz_address");

	@Override
	public java.lang.String getgetmesxmlPortAddress() {
		return getmesxmlPort_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String getmesxmlPortWSDDServiceName = "getmesxmlPort";

	public java.lang.String getgetmesxmlPortWSDDServiceName() {
		return getmesxmlPortWSDDServiceName;
	}

	public void setgetmesxmlPortWSDDServiceName(java.lang.String name) {
		getmesxmlPortWSDDServiceName = name;
	}

	@Override
	public GetmesxmlPortType getgetmesxmlPort()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(getmesxmlPort_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getgetmesxmlPort(endpoint);
	}

	@Override
	public GetmesxmlPortType getgetmesxmlPort(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			GetmesxmlBindingStub _stub = new GetmesxmlBindingStub(portAddress,
					this);
			_stub.setPortName(getgetmesxmlPortWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setgetmesxmlPortEndpointAddress(java.lang.String address) {
		getmesxmlPort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	@Override
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (GetmesxmlPortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				GetmesxmlBindingStub _stub = new GetmesxmlBindingStub(
						new java.net.URL(getmesxmlPort_address), this);
				_stub.setPortName(getgetmesxmlPortWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	@Override
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("getmesxmlPort".equals(inputPortName)) {
			return getgetmesxmlPort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	@Override
	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName(
				"http://www.bzezt.com/soap/getmesxml", "getmesxml");
	}

	private java.util.HashSet ports = null;

	@Override
	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://www.bzezt.com/soap/getmesxml", "getmesxmlPort"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("getmesxmlPort".equals(portName)) {
			setgetmesxmlPortEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
