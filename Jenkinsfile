pipeline {
  agent any
  stages {
    stage('Build image') {
      steps {
        sh 'docker build -t teedy2024_manual .'
      }
    }
    stage('upload image') {
      steps {
        sh 'docker push lamptales/teedy_local:v1.0'
      }
    }
    stage('running container') {
      steps {
        sh 'docker pull lamptales/teedy_local:v1.0'
        sh 'docker run -d -p 8084:8080 lamptales/teedy_local:v1.0'
        sh 'docker run -d -p 8085:8080 lamptales/teedy_local:v1.0'
        sh 'docker run -d -p 8086:8080 lamptales/teedy_local:v1.0'
      }
    }
  }
  post {
    always {
      archiveArtifacts artifacts: '**/target/site/**', fingerprint: true
      archiveArtifacts artifacts: '**/target/**/*.jar', fingerprint: true
      archiveArtifacts artifacts: '**/target/**/*.war', fingerprint: true
    }
  }
}
