require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-upshotsdk"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-upshot
                   DESC
  s.homepage     = "http://www.upshot.ai/"
  s.documentation_url = "http://www.upshot.ai/documentation/sdk/recatNative/"
  s.social_media_url = "https://twitter.com/upshot_ai"
  s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "Your Name" => "yourname@email.com" }
  s.platforms    = { :ios => "8.0" }
  s.source       = { :git => "https://github.com/github_account/react-native-upshot.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  
  s.dependency "Upshot-iOS-SDK"
end


