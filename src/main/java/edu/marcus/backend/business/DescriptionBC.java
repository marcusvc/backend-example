package edu.marcus.backend.business;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import edu.marcus.backend.business.model.Description;
import edu.marcus.backend.persistence.DescriptionDAO;
import edu.marcus.backend.persistence.model.DescriptionEntity;

/**
 * Description business controller
 */
@Dependent
public class DescriptionBC {
	
	@Inject
	private DescriptionDAO descriptionDAO;
	
	/**
	 * Retrieves a description bean
	 * @return description bean
	 */
	public Description retrieve() {
		DescriptionEntity descriptionEntity = descriptionDAO.retrieve();
		
		// implement some business logic
		
		return new Description(descriptionEntity);
	}

}
