

public class Participant {
	private String _name, _contact, _email, _address, _matric, _remark;
	private int id;
	
	public Participant(String name, String matric, String contact, String emailadd, String homeadd, String remarks) {
		_name = name;
		_contact = contact;
		_email = emailadd;
		_address = homeadd;
		_matric = matric;
		_remark = remarks;
	}

	public int getID(){
		return id;
	}


	public void setID(int id){
		this.id = id;
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public String getContact() {
		return _contact;
	}
	
	public void setContact(String contact) {
		this._contact = contact;
	}

	public String getEmail() {
		return _email;
	}

	public void setEmail(String email) {
		this._email = email;
	}

	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		this._address = address;
	}

	public String getMatric() {
		return _matric;
	}

	public void setMatric(String matric) {
		this._matric = matric;
	}

	public String getRemark() {
		return _remark;
	}

	public void setRemark(String remark) {
		this._remark = remark;
	}
	
}

