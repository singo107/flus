/* 
 * @Author: singo
 */
(function ($) {

    'use strict'

    var login = {

        __form: null,
        __errorMsgBox: null,

        __accountName: null,

        __password: null,

        __loginType: 'normal',

        __toggleLogin: null,

        __errorType: '',

        __canClear: false,

        __ukeyPlugin: null,

        init: function () {
            var self = this
            self.__toggleLogin = $('.j-login')
            self.__form = $('#glodon_login_normal')
            self.__formMessage = $('#glodon_login_message')
            self.__errorMsgBox = $('#errormsg-box')
            self.__accountName = self.__form.find('input[name=j_username]')
            self.__ukeyPlugin = App.modules.Plugin
            self.bindEvent()
            self.dealErrorMsgBox()
            self.simulatePlaceholder()
        },

        checkUKey: function () {
            try{
                if(this.__ukeyPlugin.__hasPlugin() && this.__ukeyPlugin.__hasUKey()){
                    window.location.href =visitor.rootPath + "/login/ukey"
                    return false;
                }
            }catch(e){

            }

        },

        dealErrorMsgBox: function () {
            var self = this
            var errorMsg = self.__errorMsgBox.html()
            if (errorMsg) {
                self.__errorMsgBox.html(self.__tipText.errorMsgAfterLogin[errorMsg](self.__accountName.val())).show()
            }
        },

        initCaptcha: function () {
            var self = this
            var captchaInp = self.__formMessage.find('input[name="author-code"]')
            if (!captchaInp.length) return
            self.captcha = $('.code-pic').captcha({
                callback: function (tag) {
                    $(this).attr('data-result', tag)
                    if (!tag) {
                        self.__showErrorTip(captchaInp, self.__tipText.captcha.error)
                    } else {
                        self.__showSuccessTip(captchaInp)
                    }
                }
            })
            $('.j-change-pic').click(function () {
                self.captcha.refresh()
                captchaInp.focus()
                return false
            })
        },

        simulatePlaceholder: function () {
            var self = this

            self.__accountName = self.__form.find('input[name=j_username]').placeholder(true).focus()
            self.__password = self.__form.find('input[name=j_password]').placeholder(true)
            self.__phoneNumber = self.__form.find('input[name=mobile]').placeholder(true)
            self.__dyPwd = self.__form.find('input[name=code]').placeholder(true)
        },

        bindEvent: function () {
            var self = this
            self.__form.on('submit', function () {
                return self.__submitCheck()
            })
            self.__formMessage.on('submit', function () {
                return self.__submitMessageCheck()
            })
            self.toggleLoginType()
            self.__toggleLogin.eq(1).trigger('click')
            self.messageLoginCheck()
            self.__form.find('input').focus(function () {
                $(this).parent().addClass('input-focus')
            }).blur(function () {
                $(this).parent().removeClass('input-focus')
            })
            self.__form.on('click', 'label.checkbox', function () {
                var cb = $(this).find('.rememberme-cb').toggleClass('checked')
                self.__form.find('input[name="rememberMe"]').prop('checked', cb.is('.checked'))
                return false
            })
        },

        toggleLoginType: function () {
            var self = this
            var $loginTrigger = self.__toggleLogin
            $loginTrigger.on('click', function (e) {
                e.preventDefault()
                self.__loginType = $(this).data('type')
                $('.login-type').each(function () {
                    if (this.className.indexOf(self.__loginType) > -1) {
                        $(this).show()
                    } else {
                        $(this).hide()
                    }
                })
                // self.clearErrorTips()
                self.__errorMsgBox.hide()
                App.common.modules.i18n.clearMessageBundle('footer')
                if (self.__loginType === 'message') {
                    self.initCaptcha()
                    App.common.modules.footer.init(null, {
                        noLanguageSetting: true
                    })
                } else {
                    App.common.modules.footer.init()
                }
                return false
            })
        },

        clearErrorTips: function () {
            var self = this
            if (self.__errorType && self.__canClear) {
                self.__errorMsgBox.html('').hide()
            }

            if (self.__errorType !== 'invalidCode' && window.location.hash === '#messageLogin') {
                self.__errorMsgBox.html('').hide()
            }

            if (self.__errorType === 'invalidCode' && window.location.hash !== '#messageLogin') {
                self.__errorMsgBox.html('').hide()
            }
            self.__canClear = true
        },

        messageLoginCheck: function () {
            var self = this
            var codeNum
            var phoneNum
            var pwdNum
            var isPhoneRegisted = false
            var $inputPhone = self.__formMessage.find('input[name="mobile"]')
            var $inputCode = self.__formMessage.find('input[name="author-code"]')
            var $buttonPwd = self.__formMessage.find('.j-send-pwd')
            var $inputPwd = self.__formMessage.find('input[name="code"]')

            $inputPhone.blur(function () {
                var $this = $(this)
                $buttonPwd.prop('disabled', true)
                phoneNum = parseInt($.trim($this.val()))
                if (!phoneNum) {
                    self.__errorMsgBox.html(self.__tipText.mobile.tips.empty).show()
                    $this.data('isPassed', false)
                } else if (!self.__tipText.mobile.pattern.test(phoneNum)) {
                    self.__errorMsgBox.html(self.__tipText.mobile.tips.format).show()
                    $this.data('isPassed', false)
                } else {
                    App.modules.accountUtil.checkIdentity(phoneNum, function (data) {
                        if (data.exsit) {
                            isPhoneRegisted = true
                        }
                        if (!isPhoneRegisted) {
                            self.__errorMsgBox.html(self.__tipText.mobile.tips.unregister($('.reg-btn').attr('href'))).show()
                            $this.data('isPassed', false)
                        } else {
                            $this.data('isPassed', true)
                            self.__errorMsgBox.html('').hide()
                            if ($inputCode.data('isPassed')) {
                                $buttonPwd.prop('disabled', false)
                            } else if ($inputCode.data('isPassed') === false) {
                                $inputCode.trigger('blur')
                            }
                        }
                    })
                }
            })
            $inputCode.blur(function () {
                var $this = $(this)
                $buttonPwd.prop('disabled', true)
                codeNum = $.trim($this.val())
                if (!codeNum) {
                    self.__errorMsgBox.html(self.__tipText.code.tips.empty).show()
                    $this.data('isPassed', false)
                } else {
                    $.ajax({
                        url: visitor.rootPath + '/captcha/verify.json',
                        async: false,
                        type: 'POST',
                        dataType: 'json',
                        data: {captchaValue: codeNum}
                    })
                        .done(function (data) {
                            if (data.result !== 'true') {
                                self.__errorMsgBox.html(self.__tipText.code.tips.format).show()
                                $this.data('isPassed', false)
                            } else {
                                $this.data('isPassed', true)
                                self.__errorMsgBox.html('').hide()
                                if ($inputPhone.data('isPassed')) {
                                    $buttonPwd.prop('disabled', false)
                                } else if ($inputPhone.data('isPassed') === false) {
                                    $inputPhone.trigger('blur')
                                }
                            }
                        })
                }
            })
            $buttonPwd.on('click', function () {
                var $this = $(this)
                var timer = 60,
                    i = timer,
                    flag
                flag = setInterval(function () {
                    if (i < 0) {
                        i = timer
                        $this.text(App.common.modules.i18n.get('resend', true, i18nNamespace))
                        $this.prop('disabled', false)
                        clearInterval(flag)
                    } else {
                        $this.text(App.common.modules.i18n.get('resend', true, i18nNamespace) + '（' + i + '）')
                        $this.prop('disabled', true)
                    }
                    i--
                }, 1000)

                $.ajax({
                    url: visitor.rootPath + '/sendVerificationCode.json?mobile=' + phoneNum + '&imageCaptchaValue=' + codeNum + '&type=login',
                    type: 'POST',
                    dataType: 'json'
                })
                    .done(function (data) {
                        if (data.result === 'false') {
                            self.__errorMsgBox.html(data.return_msg).show()
                        } else if (data.result === 'true') {
                            self.__errorMsgBox.html('').hide()
                        }
                    })
            })

            $inputPwd.blur(function () {
                var $this = $(this)

                pwdNum = $.trim($this.val())

                if (!pwdNum) {
                    $this.data('isPassed', false)
                    self.__errorMsgBox.html(self.__tipText.pwd.tips.empty).show()
                } else {
                    $this.data('isPassed', true)
                    self.__errorMsgBox.html('').hide()
                }

            })
        },

        __submitCheck: function () {
            var self = this,
                result = true

            self.__accountName.val($.trim(self.__accountName.val()))
            if (self.__accountName.val() === '') {
                result = false
                self.__errorMsgBox.html(self.__tipText.accountName.info).show()
                self.__accountName.focus()
            } else if (self.__password.val() === '') {
                result = false
                self.__errorMsgBox.html(self.__tipText.password.info).show()
                self.__password.focus()
            } else {
                self.__errorMsgBox.hide()
            }

            return result
        },

        __submitMessageCheck: function () {

            var self = this,
                result = true

            var $inputPhone = self.__formMessage.find('input[name="mobile"]'),
                $inputCode = self.__formMessage.find('input[name="author-code"]'),
                $inputPwd = self.__formMessage.find('input[name="code"]')

            if (!$inputPhone.data('isPassed')) {
                $inputPhone.trigger('blur').focus()
                result = false
            } else if (!$inputCode.data('isPassed')) {
                $inputCode.trigger('blur').focus()
                result = false
            } else if (!$inputPwd.data('isPassed')) {
                $inputPwd.trigger('blur').focus()
                result = false
            }

            return result
        },

        __tipText: {
            accountName: {
                info: function () {
                    return App.common.modules.i18n.get('inputAccountName', true, i18nNamespace)
                }
            },
            password: {
                info: function () {
                    return App.common.modules.i18n.get('inputPassword', true, i18nNamespace)
                }
            },
            errorMsgAfterLogin: {
                credential: function () {
                    return App.common.modules.i18n.get('errorMsgAfterLoginCredential', true, i18nNamespace)
                },
                verified: function (accountName) {
                    return App.common.modules.i18n.get('errorMsgAfterLoginVerified', [visitor.rootPath, accountName], i18nNamespace)
                },
                others: function () {
                    return App.common.modules.i18n.get('errorMsgAfterLoginOthers', true, i18nNamespace)
                },
                invalidCode: function () {
                    return App.common.modules.i18n.get('errorMsgAfterLoginInvalidCode', true, i18nNamespace)
                }
            },
            mobile: {
                tips: {
                    empty: function () {
                        return App.common.modules.i18n.get('mobileEmpty', true, i18nNamespace)
                    },
                    format: function () {
                        return App.common.modules.i18n.get('mobileWrongFormat', true, i18nNamespace)
                    },
                    unregister: function (regUrl) {
                        return App.common.modules.i18n.get('mobileUnregister', [regUrl], i18nNamespace)
                    }
                },
                pattern: /^1[3|4|5|8][0-9]\d{8}$/
            },
            code: {
                tips: {
                    empty: function () {
                        return App.common.modules.i18n.get('codeEmpty', true, i18nNamespace)
                    },
                    format: function () {
                        return App.common.modules.i18n.get('codeWrongFormat', true, i18nNamespace)
                    },
                    forbid: function () {
                        return App.common.modules.i18n.get('codeForbidded', true, i18nNamespace)
                    },
                    wrong: function () {
                        return App.common.modules.i18n.get('codeWrong', true, i18nNamespace)
                    }
                }
            },
            pwd: {
                tips: {
                    empty: function () {
                        return App.common.modules.i18n.get('passwordEmpty', true, i18nNamespace)
                    },
                    error: function () {
                        return App.common.modules.i18n.get('passwordError', true, i18nNamespace)
                    }
                }
            }
        }
    }

    $(function () {
        App.common.modules.i18n.setMessageBundle(window.loginPageI18nMessages, i18nNamespace)
        login.init()
    })

})(jQuery)
