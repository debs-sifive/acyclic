package acyclic

import utest._
import TestUtils.{make, makeFail}
import scala.tools.nsc.util.ScalaClassLoader.URLClassLoader


object CycleTests extends TestSuite{

  def tests = TestSuite{
    "fail" - {

      "simple" - makeFail("fail/simple", Seq(
        "B.scala" -> Set(4, 5),
        "A.scala" -> Set(6)
      ))
      "indirect" - makeFail("fail/indirect", Seq(
        "A.scala" -> Set(6),
        "B.scala" -> Set(3),
        "C.scala" -> Set(4)
      ))
      "cyclicgraph" - makeFail("fail/cyclicgraph",
        Seq(
          "A.scala" -> Set(5),
          "E.scala" -> Set(5)
        ),
        Seq(
          "E.scala" -> Set(6),
          "D.scala" -> Set(6),
          "C.scala" -> Set(4, 5),
          "A.scala" -> Set(5)
        )
      )
      "cyclicpackage" - makeFail("fail/cyclicpackage", Nil)
    }
    "success" - {
      "simple" - make("success/simple")
      "cyclicunmarked" - make("success/cyclicunmarked")
      "dag" - make("success/dag")
      "singlepackage" - make("success/singlepackage")
      "cyclicpackage" - make("success/cyclicpackage")
    }
    "self" - make("../../main/scala", extraIncludes = Nil)
  }
}


