package com.shuwoom.apihooker.common;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class InterceptEvent implements Parcelable {
	
	private UUID idEvent;
	
	private String IDXP;
	
	private long timestamp;
	
	private long relativeTimestamp;
	
	private String hookerName;
	//0:Nothing related personal info
	//1:Reading personal info
	//2:Writing personal info
	
	private int intrusiveLevel;
	
	private int instanceID;
	
	private String packageName;
	
	private String className;
	
	private String methodName;
	
	private List<Entry<String, String>> parameters = new ArrayList<Entry<String, String>>();
	
	private Entry<String, String> returns;
	
	private Map<String, String> data = new HashMap<String, String>();
	
	

	public InterceptEvent(Parcel in) {
		idEvent = UUID.fromString(in.readString());
		IDXP = in.readString();
		timestamp = in.readLong();
		relativeTimestamp = in.readLong();
		hookerName = in.readString();
		intrusiveLevel = in.readInt();
		instanceID = in.readInt();
		packageName = in.readString();
		className = in.readString();
		methodName = in.readString();
		
		readParameterList(in);
		readReturnsEntry(in);
		readDataMap(in);
		
	}

	private void readDataMap(Parcel in) {
		int size = in.readInt();
		if(size != 0){
			for(int i = 0; i < size; i++){
				String key = in.readString();
				String value = in.readString();
				data.put(key, value);
			}
		}
	}

	private void readReturnsEntry(Parcel in) {
		int tmp = in.readInt();
		if(tmp != 0){
			String key = in.readString();
			String value = in.readString();
			returns = new AbstractMap.SimpleEntry<String, String>(key, value);
		}
	}

	private void readParameterList(Parcel in) {
		int size = in.readInt();
		if(size != 0){
			for(int i = 0; i < size; i++){
				String key = in.readString();
				String value = in.readString();
				parameters.add(new AbstractMap.SimpleEntry<String, String>(key, value));
			}
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(idEvent.toString());
		dest.writeString(IDXP);
		dest.writeLong(timestamp);
		dest.writeLong(relativeTimestamp);
		dest.writeString(hookerName);
		dest.writeInt(intrusiveLevel);
		dest.writeInt(instanceID);
		dest.writeString(packageName);
		dest.writeString(className);
		dest.writeString(methodName);
		
		writeParametersList(dest);
		writeReturnsEntry(dest);
		writeDataMap(dest);
	}

	private void writeDataMap(Parcel dest) {
		if(parameters == null){
			dest.writeInt(0);
		}else{
			dest.writeInt(parameters.size());
			for(Entry<String, String> entry : parameters){
				dest.writeString(entry.getKey());
				dest.writeString(entry.getValue());
			}
		}
	}

	private void writeReturnsEntry(Parcel dest) {
		if(returns == null){
			dest.writeInt(0);
		}else{
			dest.writeInt(1);
			dest.writeString(returns.getKey());
			dest.writeString(returns.getValue());
		}
	}

	private void writeParametersList(Parcel dest) {
		if(data == null){
			dest.writeInt(0);
		}else{
			dest.writeInt(data.size());
			for(String key : data.keySet()){
				dest.writeString(key);
				dest.writeString(data.get(key));
			}
		}
	}
	
	public static final Parcelable.Creator<InterceptEvent> CREATOR = new Parcelable.Creator<InterceptEvent>() {
		public InterceptEvent createFromParcel(Parcel in) {
			return new InterceptEvent(in);
		}

		public InterceptEvent[] newArray(int size) {
			return new InterceptEvent[size];
		}
	};



	public InterceptEvent(String hookerName, int intrusiveLevel,
			int instanceID, String packageName, String className,
			String methodName) {
		this(buildRandomUUID(), 0l, 0, hookerName, intrusiveLevel, instanceID, packageName, 
				className, methodName, null, null, null);
	}
	
	
	
	public InterceptEvent(UUID idEvent, long timestamp, long relativeTimestamp, String hookerName, int intrusiveLevel, 
	  		int instanceId, String packageName,
	  	String className, String methodName, List<Entry<String, String>> parameters,
        Entry<String, String> returns, Map<String, String> data){
		super();
		this.idEvent = idEvent;
	    if (timestamp == 0) {
	      try {
	        timestamp = Calendar.getInstance().getTime().getTime();
	      }catch(Exception e) {
	        timestamp = 0;
//	        SubstrateMain.log("Error while computing the current timestamp...", e);
	      }
	    }
	    this.timestamp = timestamp;
	    this.relativeTimestamp = relativeTimestamp;
	    this.hookerName = hookerName;
	    this.intrusiveLevel = intrusiveLevel;
	    this.instanceID = instanceId;
	    this.packageName = packageName;
	    this.className = className;
	    this.methodName = methodName;
	    this.parameters = parameters;
	    this.returns = returns;
	    this.data = data;
	}

	private static UUID buildRandomUUID() {
	    try {   
	      return UUID.randomUUID();
	    } catch (Exception e) {
	      return UUID.fromString("00000000-1111-2222-3333-444444444444");
	    }
	  }
	
	public void addParameter(String type, String value) {
	    if (this.parameters == null) {
	      this.parameters = new ArrayList<Map.Entry<String,String>>();
	    }
	    this.parameters.add(new AbstractMap.SimpleEntry<String, String>(type, value));
	  }

	public UUID getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(UUID idEvent) {
		this.idEvent = idEvent;
	}

	public String getIDXP() {
		return IDXP;
	}

	public void setIDXP(String iDXP) {
		IDXP = iDXP;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getRelativeTimestamp() {
		return relativeTimestamp;
	}

	public void setRelativeTimestamp(long relativeTimestamp) {
		this.relativeTimestamp = relativeTimestamp;
	}

	public String getHookerName() {
		return hookerName;
	}

	public void setHookerName(String hookerName) {
		this.hookerName = hookerName;
	}

	public int getIntrusiveLevel() {
		return intrusiveLevel;
	}

	public void setIntrusiveLevel(int intrusiveLevel) {
		this.intrusiveLevel = intrusiveLevel;
	}

	public int getInstanceID() {
		return instanceID;
	}

	public void setInstanceID(int instanceID) {
		this.instanceID = instanceID;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<Entry<String, String>> getParameters() {
		return parameters;
	}

	public void setParameters(List<Entry<String, String>> parameters) {
		this.parameters = parameters;
	}

	public Entry<String, String> getReturns() {
		return returns;
	}

	public void setReturns(Entry<String, String> returns) {
		this.returns = returns;
	}
	
	public void setReturns(String type, String value) {
	    this.returns = new AbstractMap.SimpleEntry<String, String>(type, value);
	    
	  }

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterceptEvent [idEvent=");
		builder.append(idEvent);
		builder.append(", IDXP=");
		builder.append(this.IDXP);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", hookerName=");
		builder.append(hookerName);
		builder.append(", intrusiveLevel=");
		builder.append(intrusiveLevel);
		builder.append(", instanceID=");
		builder.append(instanceID);
		builder.append(", packageName=");
		builder.append(packageName);
		builder.append(", className=");
		builder.append(className);
		builder.append(", methodName=");
		builder.append(methodName);
		builder.append("]");
		return builder.toString();
	}
	
	public String toJson() {
	    JSONObject object = new JSONObject();

	    try {
//	      object.put("IdEvent", this.getIdEvent().toString());

	      object.put("Timestamp", this.getTimestamp());
	      object.put("RelativeTimestamp", this.getRelativeTimestamp());
	      object.put("HookerName", this.getHookerName());
	      object.put("IntrusiveLevel", this.getIntrusiveLevel());
	      object.put("InstanceID", this.getInstanceID());
	      object.put("PackageName", this.getPackageName());
	      
	      object.put("ClassName", this.getClassName());
	      object.put("MethodName", this.getMethodName());

	      JSONArray parameters = new JSONArray();
	      if (this.getParameters() != null) {
	        for (Entry<String, String> parameter : this.getParameters()) {
	          JSONObject jsonParameter = new JSONObject();
	          jsonParameter.put("ParameterType", parameter.getKey());
	          jsonParameter.put("ParameterValue", parameter.getValue());
	          parameters.put(jsonParameter);
	        }
	      }
	      object.put("Parameters", parameters);

	      JSONObject returns = new JSONObject();
	      if (this.getReturns() != null) {
	        returns.put("ReturnType", this.getReturns().getKey());
	        returns.put("ReturnValue", this.getReturns().getValue());
	      }
	      object.put("Return", returns);


	      JSONArray data = new JSONArray();
	      if (this.getData() != null) {
	        for (String dataName : this.getData().keySet()) {
	          if (dataName != null && this.getData().get(dataName) != null) {
	            JSONObject dataP = new JSONObject();
	            dataP.put("DataName", dataName);
	            dataP.put("DataValue", this.getData().get(dataName));
	          }
	        }
	      }
	      object.put("Data", data);
	    } catch (JSONException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    return object.toString();
	  }

}
