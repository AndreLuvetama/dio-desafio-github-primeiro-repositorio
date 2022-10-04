package me.dio.sacola.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumaration.FormaPagamento;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ItemRepository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.service.SacolaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
    private final SacolaRepository sacolaResiposy;
    private final ProdutoRepository produtoResiposy;
    private final ItemRepository itemRepository;
    @Override
    public Item incluirItemSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getIdSacola());
        if(sacola.isFechado()){
            throw new RuntimeException("Esta sacola está fechada");
        }
        Item itemParaInserir = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoResiposy.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Esse produto não existe");
                        }
                ))
                .build();
        List<Item> itensDaSacola = sacola.getItens();
        if(itensDaSacola.isEmpty()){
            itensDaSacola.add(itemParaInserir);
        } else{
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante(); //pega o primeiro item e coloca na sacola
            Restaurante restauranteDoItemAdicionar = itemParaInserir.getProduto().getRestaurante();
            if(restauranteAtual.equals(restauranteDoItemAdicionar)){
                itensDaSacola.add(itemParaInserir);
            }else{
                throw new RuntimeException("Não é possivel adicicionar produtos de Restaurantes diferentes, fecha a Sacola ou esvazia");
            }
        }

        List<Double> valorDosItens = new ArrayList<>();
        for(Item itemDaSacola : itensDaSacola){
            double valorTotalItens = itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade(); // pega cada item da sac e mult com a qtid
            valorDosItens.add(valorTotalItens);
        }
        // Pega o valor total de cada item e soma
        double valorTotalDaSacola = valorDosItens.stream().mapToDouble(valorTotalDeCadaItem->valorTotalDeCadaItem).sum();

        sacola.setValorTotal(valorTotalDaSacola);
        sacolaResiposy.save(sacola);
        return itemParaInserir;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaResiposy.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe");
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {

        Sacola sacola = verSacola(id);
        if(sacola.getItens().isEmpty()){
            throw new RuntimeException("Inclua intens na sacola");
        }
        FormaPagamento formaPagamento = numeroFormaPagamento == 0 ?
                FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;
        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechado(true);
        return sacolaResiposy.save(sacola);
    }
}
