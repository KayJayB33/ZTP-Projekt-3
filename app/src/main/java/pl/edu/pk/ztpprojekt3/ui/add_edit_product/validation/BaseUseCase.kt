package pl.edu.pk.ztpprojekt3.ui.add_edit_product.validation

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}