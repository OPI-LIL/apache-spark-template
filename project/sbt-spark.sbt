resolvers += Resolver.url(
    "opi-lil-sbt-plugins", 
      url("http://dl.bintray.com/1o0ko/sbt-plugins"))(
        Resolver.ivyStylePatterns)

addSbtPlugin("com.opi.lil" % "sbt-spark" % "0.1.7")