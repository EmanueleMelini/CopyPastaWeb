package http.responses

import kotlinx.serialization.Serializable
import model.CopyPastaModel

@Serializable
data class CopyPastaResponse(
    val error: Int,
    val message: String,
    val copys: Array<CopyPastaModel>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as CopyPastaResponse

        if (error != other.error) return false
        if (message != other.message) return false
        if (copys != null) {
            if (other.copys == null) return false
            if (!copys.contentEquals(other.copys)) return false
        } else if (other.copys != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = error
        result = 31 * result + message.hashCode()
        result = 31 * result + (copys?.contentHashCode() ?: 0)
        return result
    }
}