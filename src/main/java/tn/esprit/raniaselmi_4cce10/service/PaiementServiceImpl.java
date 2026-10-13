package tn.esprit.raniaselmi_4cce10.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.raniaselmi_4cce10.domain.Paiement;
import tn.esprit.raniaselmi_4cce10.exception.ResourceNotFoundException;
import tn.esprit.raniaselmi_4cce10.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }
}
