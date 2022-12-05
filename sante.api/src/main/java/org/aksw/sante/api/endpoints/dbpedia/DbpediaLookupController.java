package org.aksw.sante.api.endpoints.dbpedia;

import java.util.*;

import org.aksw.sante.api.endpoints.dbpedia.data.DbpediaDocumentCollection;
import org.aksw.sante.api.exception.SearchSuggestException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * SpringBoot controller for the dbpedia-lookup endpoint.
 *
 * @see RestController
 */
@RestController
public class DbpediaLookupController implements DbpediaLookupOperations {

	/**
	 * The service to perform searches analogously to DBpedia-Lookup.
	 */
	private final DbpediaLookupService dbpediaLookupService;

	/**
	 * Constructs the controller and injects all necessary dependencies.
	 *
	 * @param dbpediaLookupService the service to perform the search
	 * @see DbpediaLookupService
	 */
	public DbpediaLookupController(DbpediaLookupService dbpediaLookupService) {
		this.dbpediaLookupService = dbpediaLookupService;
	}

	@Override
	public DbpediaDocumentCollection lookupDbpedia(
			@NotNull @Min(1) Integer maxHits,
			Optional<String> searchQuery,
			Optional<String> searchClasses
	) throws SearchSuggestException {
		return this.dbpediaLookupService.lookupDbpedia(
				maxHits,
				searchQuery.orElse(""),
				// Validation for proper comma separated list of classes is done implicitly through parsing the string
				searchClasses.map(classes -> new HashSet<>(Arrays.asList(classes.split(","))))
						.orElseGet(HashSet::new)
		);
	}
}
