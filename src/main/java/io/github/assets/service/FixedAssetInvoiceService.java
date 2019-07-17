package io.github.assets.service;

import io.github.assets.service.dto.FixedAssetInvoiceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.FixedAssetInvoice}.
 */
public interface FixedAssetInvoiceService {

    /**
     * Save a fixedAssetInvoice.
     *
     * @param fixedAssetInvoiceDTO the entity to save.
     * @return the persisted entity.
     */
    FixedAssetInvoiceDTO save(FixedAssetInvoiceDTO fixedAssetInvoiceDTO);

    /**
     * Get all the fixedAssetInvoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetInvoiceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fixedAssetInvoice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FixedAssetInvoiceDTO> findOne(Long id);

    /**
     * Delete the "id" fixedAssetInvoice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fixedAssetInvoice corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetInvoiceDTO> search(String query, Pageable pageable);
}
