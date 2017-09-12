package org.eclipse.dsp4e.example.readme.debugmodel;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

final class DebugValue implements IValue{

@Override
public String getModelIdentifier() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public IDebugTarget getDebugTarget() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ILaunch getLaunch() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public <T> T getAdapter(Class<T> adapter) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getReferenceTypeName() throws DebugException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getValueString() throws DebugException {
	// TODO Auto-generated method stub
	return "Hello";
}

@Override
public boolean isAllocated() throws DebugException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public IVariable[] getVariables() throws DebugException {
	// TODO Auto-generated method stub
	IVariable[] myArr = new IVariable[2];
	DebugVariable debugVariable = new DebugVariable("Art");
	DebugVariable debugVariable2 = new DebugVariable("KC");
	myArr[1] = debugVariable2;
	myArr[0] = debugVariable;
	return myArr;
}

@Override
public boolean hasVariables() throws DebugException {
	// TODO Auto-generated method stub
	return true;
}
}