/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CwipTransferComponentsPage, CwipTransferDeleteDialog, CwipTransferUpdatePage } from './cwip-transfer.page-object';

const expect = chai.expect;

describe('CwipTransfer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cwipTransferUpdatePage: CwipTransferUpdatePage;
  let cwipTransferComponentsPage: CwipTransferComponentsPage;
  let cwipTransferDeleteDialog: CwipTransferDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CwipTransfers', async () => {
    await navBarPage.goToEntity('cwip-transfer');
    cwipTransferComponentsPage = new CwipTransferComponentsPage();
    await browser.wait(ec.visibilityOf(cwipTransferComponentsPage.title), 5000);
    expect(await cwipTransferComponentsPage.getTitle()).to.eq('Cwip Transfers');
  });

  it('should load create CwipTransfer page', async () => {
    await cwipTransferComponentsPage.clickOnCreateButton();
    cwipTransferUpdatePage = new CwipTransferUpdatePage();
    expect(await cwipTransferUpdatePage.getPageTitle()).to.eq('Create or edit a Cwip Transfer');
    await cwipTransferUpdatePage.cancel();
  });

  it('should create and save CwipTransfers', async () => {
    const nbButtonsBeforeCreate = await cwipTransferComponentsPage.countDeleteButtons();

    await cwipTransferComponentsPage.clickOnCreateButton();
    await promise.all([
      cwipTransferUpdatePage.setTransferMonthInput('2000-12-31'),
      cwipTransferUpdatePage.setAssetSerialTagInput('assetSerialTag'),
      cwipTransferUpdatePage.setServiceOutletCodeInput('serviceOutletCode'),
      cwipTransferUpdatePage.setTransferTransactionIdInput('5'),
      cwipTransferUpdatePage.setAssetCategoryIdInput('5'),
      cwipTransferUpdatePage.setCwipTransactionIdInput('5'),
      cwipTransferUpdatePage.setTransferDetailsInput('transferDetails'),
      cwipTransferUpdatePage.setTransferAmountInput('5'),
      cwipTransferUpdatePage.setDealerIdInput('5'),
      cwipTransferUpdatePage.setTransactionInvoiceIdInput('5')
    ]);
    expect(await cwipTransferUpdatePage.getTransferMonthInput()).to.eq(
      '2000-12-31',
      'Expected transferMonth value to be equals to 2000-12-31'
    );
    expect(await cwipTransferUpdatePage.getAssetSerialTagInput()).to.eq(
      'assetSerialTag',
      'Expected AssetSerialTag value to be equals to assetSerialTag'
    );
    expect(await cwipTransferUpdatePage.getServiceOutletCodeInput()).to.eq(
      'serviceOutletCode',
      'Expected ServiceOutletCode value to be equals to serviceOutletCode'
    );
    expect(await cwipTransferUpdatePage.getTransferTransactionIdInput()).to.eq(
      '5',
      'Expected transferTransactionId value to be equals to 5'
    );
    expect(await cwipTransferUpdatePage.getAssetCategoryIdInput()).to.eq('5', 'Expected assetCategoryId value to be equals to 5');
    expect(await cwipTransferUpdatePage.getCwipTransactionIdInput()).to.eq('5', 'Expected cwipTransactionId value to be equals to 5');
    expect(await cwipTransferUpdatePage.getTransferDetailsInput()).to.eq(
      'transferDetails',
      'Expected TransferDetails value to be equals to transferDetails'
    );
    expect(await cwipTransferUpdatePage.getTransferAmountInput()).to.eq('5', 'Expected transferAmount value to be equals to 5');
    expect(await cwipTransferUpdatePage.getDealerIdInput()).to.eq('5', 'Expected dealerId value to be equals to 5');
    expect(await cwipTransferUpdatePage.getTransactionInvoiceIdInput()).to.eq('5', 'Expected transactionInvoiceId value to be equals to 5');
    await cwipTransferUpdatePage.save();
    expect(await cwipTransferUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cwipTransferComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CwipTransfer', async () => {
    const nbButtonsBeforeDelete = await cwipTransferComponentsPage.countDeleteButtons();
    await cwipTransferComponentsPage.clickOnLastDeleteButton();

    cwipTransferDeleteDialog = new CwipTransferDeleteDialog();
    expect(await cwipTransferDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Cwip Transfer?');
    await cwipTransferDeleteDialog.clickOnConfirmButton();

    expect(await cwipTransferComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
