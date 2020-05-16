package udemy.typeClass

// Type class template
trait TypeClassTemplate[T] {
  def action(value: T): String //return anything
}

// companion object
object TypeClassTemplate {
  def apply[T](implicit instance: TypeClassTemplate[T]): TypeClassTemplate[T] =
    instance
}
