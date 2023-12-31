package com.example.projectuts_anmp
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val context: Context, private var items: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tablenumber)
        val itemPrice: TextView = itemView.findViewById(R.id.cartItemPriceTextView)
        val itemQuantity: TextView = itemView.findViewById(R.id.txtTableNumber)
        val deleteCartItemButton: Button = itemView.findViewById(R.id.deleteCartItemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = items[position]
        Log.d("CartAdapter", "Jumlah item dalam cartAdapter: ${cartItem.menuItem.name}")

        // Setel tampilan item dengan data dari item keranjang
        holder.itemName.text = cartItem.menuItem.name
        holder.itemPrice.text = "Harga: ${cartItem.menuItem.price}"
        holder.itemQuantity.text = "Jumlah: ${cartItem.quantity}"
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(position)
        }
        holder.deleteCartItemButton.setOnClickListener {
            showDeleteConfirmationDialog(holder.adapterPosition)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }
    fun setItems(items: List<CartItem>) {
        this.items = ArrayList(items)
    }
    fun removeItem(position: Int) {
//        items.removeAt(position)
        notifyItemRemoved(position)
    }

    private var onItemClickListener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Hapus Item")
        builder.setMessage("Apakah Anda yakin ingin menghapus item dari keranjang?")
        builder.setPositiveButton("Ya") { _, _ ->
            // Hapus item dari keranjang jika pengguna menekan "Ya"
            removeItem(position)
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            // Batalkan penghapusan jika pengguna menekan "Tidak"
            dialog.dismiss()
        }
        builder.create().show()
    }
}
