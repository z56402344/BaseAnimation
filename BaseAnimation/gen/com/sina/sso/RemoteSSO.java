/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\androidspace\\jike\\BaseAnimation\\src\\com\\sina\\sso\\RemoteSSO.aidl
 */
package com.sina.sso;
public interface RemoteSSO extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.sina.sso.RemoteSSO
{
private static final java.lang.String DESCRIPTOR = "com.sina.sso.RemoteSSO";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.sina.sso.RemoteSSO interface,
 * generating a proxy if needed.
 */
public static com.sina.sso.RemoteSSO asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.sina.sso.RemoteSSO))) {
return ((com.sina.sso.RemoteSSO)iin);
}
return new com.sina.sso.RemoteSSO.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getPackageName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getPackageName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getActivityName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getActivityName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.sina.sso.RemoteSSO
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.lang.String getPackageName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPackageName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getActivityName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getActivityName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getPackageName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getActivityName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public java.lang.String getPackageName() throws android.os.RemoteException;
public java.lang.String getActivityName() throws android.os.RemoteException;
}
