package id.muhammadfaisal.mcommerce.api.request

/*
*  {
     "name": "Kemeja Polo Polos",
     "price": 150000,
     "stock": 100,
     "description": "Kemeja Polo Polos dengan banyak pilihan warna",
     "image": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyOpzLtGAIUfXppBQYt50e5Sf2ORDf7FkWdw&usqp=CAU"
 }
 * */
data class AddProductRequest(
    val name: String,
    val price: Long,
    val stock: Int,
    val description: String,
    val image: String
)