package participant;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Item;


class ParticipantListCellModifier implements ICellModifier {
	  private Viewer viewer;

	  public ParticipantListCellModifier(Viewer view) {
		  viewer = view;
	  }

	  /**
	   * Description: Returns whether the property can be modified
	   * 
	   * @param element
	   *         
	   * @param property
	   *         
	   * @return boolean
	   */
	  public boolean canModify(Object element, String property) {
	    // Allow editing of all values
	    return true;
	  }

	  /**
	   * Description: Returns the value for the property
	   * 
	   * @param element
	   *           
	   * @param property
	   *          
	   * @return Object
	   */
	  public Object getValue(Object element, String property) {
		  
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
	   * Description: Modifies the element based on the property selected.
	   * 
	   * @param element
	   *          
	   * @param property
	   *          
	   * @param value
	   *          
	   */
	  public void modify(Object element, String property, Object value) {

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