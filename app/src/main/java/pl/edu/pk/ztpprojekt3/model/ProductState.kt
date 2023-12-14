package pl.edu.pk.ztpprojekt3.model

enum class ProductState(private val message: String) {
    AVAILABLE("available"),
    OUT_OF_STOCK("out of stock");

    override fun toString(): String {
        return message
    }


}
