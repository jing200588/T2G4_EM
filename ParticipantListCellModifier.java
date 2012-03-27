import java.util.List;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Item;

class ParticipantListCellModifier implements ICellModifier {
	  private Viewer viewer;

	  public ParticipantListCellModifier(Viewer viewer) {
	    this.viewer = viewer;
	  }

	  /**
	   * Returns whether the property can be modified
	   * 
	   * @param element
	   *            the element
	   * @param property
	   *            the property
	   * @return boolean
	   */
	  public boolean canModify(Object element, String property) {
	    // Allow editing of all values
	    return true;
	  }

	  /**
	   * Returns the value for the property
	   * 
	   * @param element
	   *            the element
	   * @param property
	   *            the property
	   * @return Object
	   */
	  public Object getValue(Object element, String property) {
	/*    Person p = (Person) element;
	    if (PersonEditor.NAME.equals(property))
	      return p.getName();
	    else if (PersonEditor.MALE.equals(property))
	      return Boolean.valueOf(p.isMale());
	    else if (PersonEditor.AGE.equals(property))
	      return p.getAgeRange();
	    else if (PersonEditor.SHIRT_COLOR.equals(property))
	      return p.getShirtColor();
	    else
*/
/*		  String[] obj = (String[]) element;
		  
		  for(int i = 0; i < ViewParticipantList.HEADERS.length; i++)
			  if(ViewParticipantList.HEADERS[i].equals(property))
				  return obj[i];
		  return null;*/
		  
		  Participant obj = (Participant) element;
		  
		    if (property.equals("Name"))
		      	return obj.getName();
		    else if (property.equals("Matric No."))
		    	return obj.getMatric();
		    else if (property.equals("Contact"))
		    	return obj.getContact();
		    else if (property.equals("Email Address"))
		    	return obj.getEmail();
		    else if (property.equals("Home Address"))
		    	return obj.getAddress();
		    else if (property.equals("Remarks"))
		    	return obj.getRemark();
		    else
		    	return null;
	  }

	  /**
	   * Modifies the element
	   * 
	   * @param element
	   *            the element
	   * @param property
	   *            the property
	   * @param value
	   *            the value
	   */
	  public void modify(Object element, String property, Object value) {
/*	    if (element instanceof Item)
	      element = ((Item) element).getData();

	    Person p = (Person) element;
	    if (PersonEditor.NAME.equals(property))
	      p.setName((String) value);
	    else if (PersonEditor.MALE.equals(property))
	      p.setMale(((Boolean) value).booleanValue());
	    else if (PersonEditor.AGE.equals(property))
	      p.setAgeRange((Integer) value);
	    else if (PersonEditor.SHIRT_COLOR.equals(property))
	      p.setShirtColor((RGB) value);
*/
	/*	  if (element instanceof Item)
		      element = ((Item) element).getData();
		  
		  String[] obj = (String[]) element;
		  
		  for(int i = 0; i < ViewParticipantList.HEADERS.length; i++)
			  if(ViewParticipantList.HEADERS[i].equals(property))
				 obj[i] = (String) value;
		  // Force the viewer to refresh
		  viewer.refresh();
		  */
		  if (element instanceof Item)
		      element = ((Item) element).getData();
		  
		  Participant obj = (Participant) element;
		  
		  if (property.equals("Name"))
		      	obj.setName((String) value);
		    else if (property.equals("Matric No."))
		    	obj.setMatric((String) value);
		    else if (property.equals("Contact"))
		    	obj.setContact((String) value);
		    else if (property.equals("Email Address"))
		    	obj.setEmail((String) value);
		    else if (property.equals("Home Address"))
		    	obj.setAddress((String) value);
		    else if (property.equals("Remarks"))
		    	obj.setRemark((String) value);

		  // Force the viewer to refresh
		  viewer.refresh();
	  }
}