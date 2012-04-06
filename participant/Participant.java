package participant;

public class Participant {
	private String name, contact, email, address, matric, remark;
	private int id;
	
	public Participant(String inputName, String inputMatric, String inputContact, String inputEmailAdd, String inputHomeAdd, String inputRemarks) {
		name = inputName;
		contact = inputContact;
		email = inputEmailAdd;
		address = inputHomeAdd;
		matric = inputMatric;
		remark = inputRemarks;
	}

	public int getID(){
		return id;
	}


	public void setID(int inputId){
		id = inputId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String inputName) {
		name = inputName;
	}

	public String getContact() {
		return contact;
	}
	
	public void setContact(String inputContact) {
		contact = inputContact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String inputEmail) {
		email = inputEmail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String inputAddress) {
		address = inputAddress;
	}

	public String getMatric() {
		return matric;
	}

	public void setMatric(String inputMatric) {
		matric = inputMatric;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String inputRemark) {
		remark = inputRemark;
	}
	
}

