def COLOR_MAP = [
'SUCCESS': 'good',
'FAILURE': 'danger',
]
pipeline {
    agent any
    stages {
        stage('Git Clone'){
      steps {
        script {
                    echo("${env.GIT_BRANCH}")
                    if ("${env.GIT_BRANCH}" == 'origin/develop') {
                        sh 'cp src/main/resources/application.yml  src/main/resources/application.yml'
                     }
                    }
                  }
                }
    stage("Maven Build"){
      environment {
            mavenHome =  tool name: "maven-3.8.1", type: "maven"
            mavenCMD = "${mavenHome}/bin/mvn"
        }

      steps {
            sh "${mavenCMD} clean package"
               // sh "${mavenCMD} spring-boot:run"
               sh "cp target/SpringBootBlankProject.war  /www/server/tomcat9/webapps/"

          }
      }
    }

 }
