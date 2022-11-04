/* to pass the variable value run 'terraform apply'
  terraform will refer terraform.tfvars file after we execute 'apply' command
  naming the file as 'terraform.tfvars' gets autoloaded
  If file name is 'foo.tfvars' , it wont auto load
  If file name is 'foo.auto.tfvars' , it will auto load

  In order to pass the tfvars file you can execute
    terraform apply -var-file dev.tfvars

  If you want to pass values directly then
    terraform apply -var="my_instance_type=t2.large"

*/

// REFER VARIABLE DEFINATION PRECEDENCE

variable "my_instance_type" {
  type = string
  default = "t2.micro"
  description = "signifies the instance type"
}

variable "instance_tags" {
  type = object({
    Name = string
    foo = number
  })
}
