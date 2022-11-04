terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
}

provider "aws" {
  version = "~> 2.65"
  region = "eu-west-1"
}

locals {
  setup_name = "tutorial"
  foobar     = upper(upper("will"))

}

resource "aws_vpc" "siri_vpc" {
  cidr_block = "10.0.0.0/16"

  tags = {
    Name = "${local.setup_name}-vpc"
  }
}

resource "aws_subnet" "siri_subnet" {
  vpc_id = aws_vpc.siri_vpc.id
  cidr_block = "10.0.5.0/16"

  tags = {
    Name = "${local.setup_name}-subnet"
  }
}


// ec2 instance
resource "aws_instance" "siri_ec2" {
  ami = "ami-23293098"
  instance_type = var.my_instance_type
  subnet_id = aws_subnet.siri_subnet.id

  tags = var.instance_tags

}

/*
  comment document
*/

// terraform init     -- this command will download all providers
// terraform plan     -- this will give overview and do a dry run (tells what will be created)
// terraform apply    -- this will execute plan first and ask you to proceed. Then it will create all resource
// terraform destroy  -- will destroy all instances


