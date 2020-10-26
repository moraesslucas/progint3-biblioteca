package br.com.senac.biblioteca.service;

import br.com.senac.biblioteca.dto.ClienteDto;
import br.com.senac.biblioteca.enumeration.CodeEnum;
import br.com.senac.biblioteca.exception.ResourceNotFoundException;
import br.com.senac.biblioteca.model.Cliente;
import br.com.senac.biblioteca.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClienteDto findById(long matricula) {
        return modelMapper.map(
                clienteRepository.findById(matricula)
                        .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado", CodeEnum.NOT_FOUND)),
                ClienteDto.class);
    }

    @Override
    public Page<ClienteDto> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(cliente -> modelMapper.map(cliente, ClienteDto.class));
    }

    @Override
    public ClienteDto create(ClienteDto cliente) {
        return modelMapper.map(clienteRepository.save(modelMapper.map(cliente, Cliente.class)), ClienteDto.class);
    }

    @Override
    public ClienteDto update(long matricula, ClienteDto cliente) {
        cliente.setMatricula(matricula);
        return create(cliente);
    }

    @Override
    public void delete(long matricula) {
        clienteRepository.deleteById(matricula);
    }
}
