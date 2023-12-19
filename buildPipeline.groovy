node(){
 //   def sonarScanner = tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
	def repoName = "https://github.com/manimala23/SonarQubeCoverageJava"
	stage('Code Checkout'){
		git changelog: false, credentialsId: 'GitHubCreds', poll: false, url: repoName
	}
	stage('Maven Build'){
		sh """
			ls -lart
			date
			mvn clean install
		"""
	}
	/*stage('Code Review'){
		withSonarQubeEnv(credentialsId: 'SonarQubeToken') {
			sh "${sonarScanner}/bin/sonar-scanner"
		}
	}*/
	stage('Code Deployment'){
	deploy adapters: [tomcat9(credentialsId: 'tomcatcred', path: '', url: 'http://18.209.30.97:8080')], contextPath: 'counterapp', onFailure: false, war: '**/*.war'
	}
}
