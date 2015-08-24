//
// multiple profile configuration
// lishaoyan@weshare. May, 2015.
//

environments {

    // online configuration
    prod {
        jdbc {
            pay.url = 'jdbc:mysql://192.168.66.52:3306/storm?useUnicode=true&characterEncoding=utf8'
            pay.user = '__PRODUCT_CONFIG_'
            pay.password = '__PRODUCT_CONFIG_'

            thunder.url = 'jdbc:mysql://192.168.66.51:3306/thunder?useUnicode=true&characterEncoding=utf8'
            thunder.user = '__PRODUCT_CONFIG_'
            thunder.password = '__PRODUCT_CONFIG_'
        }

        redis {
            host = "192.168.66.60"
            port = 6379
            password = '__PRODUCT_CONFIG_'
        }
    }
    
    // rd devlopment configuration
    dev {
        jdbc {
            pay.url = 'jdbc:mysql://192.168.2.168:33306/storm?useUnicode=true&characterEncoding=utf8'
            pay.user = 'light_rw'
            pay.password = 'hellozzz'

            thunder.url = 'jdbc:mysql://192.168.2.168:33306/thunder?useUnicode=true&characterEncoding=utf8'
            thunder.user = 'light_rw'
            thunder.password = 'hellozzz'
        }

        redis {
            host = "192.168.2.166"
            port = 6379
            password = 'wxpay'
        }
    }

    //qa online env
    qa {
        jdbc {
            pay.url = 'jdbc:mysql://192.168.2.166:3306/storm?useUnicode=true&characterEncoding=utf8'
            pay.user = 'light_rw'
            pay.password = 'hellozzz'

            thunder.url = 'jdbc:mysql://192.168.2.166:3306/thunder?useUnicode=true&characterEncoding=utf8'
            thunder.user = 'light_rw'
            thunder.password = 'hellozzz'
        }

        redis {
            host = "192.168.2.166"
            port = 6379
            password = 'wxpay'
        }
    }
}
                
    
        
        
