output "vpc" {
  value = aws_vpc.siri_vpc
}

output "instance_ip" {
  value = aws_instance.siri_ec2.public_ip
}

output "result" {
  value = "success"
}