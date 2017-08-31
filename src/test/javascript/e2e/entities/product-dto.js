'use strict';

describe('Product e2e test', function () {

    var username = element(by.id('username'));
    var password = element(by.id('password'));
    var entityMenu = element(by.id('entity-menu'));
    var accountMenu = element(by.id('account-menu'));
    var login = element(by.id('login'));
    var logout = element(by.id('logout'));

    beforeAll(function () {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
    });

    it('should load Products', function () {
        entityMenu.click();
        element.all(by.css('[ui-sref="product-dto"]')).first().click().then(function() {
            expect(element.all(by.css('h2')).first().getText()).toMatch(/Products/);
        });
    });

    it('should load create Product dialog', function () {
        element(by.css('[ui-sref="product-dto.new"]')).click().then(function() {
            expect(element(by.css('h4.modal-title')).getText()).toMatch(/Create or edit a Product/);
            element(by.css('button.close')).click();
        });
    });

    afterAll(function () {
        accountMenu.click();
        logout.click();
    });
});
