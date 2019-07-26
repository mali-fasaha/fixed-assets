/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AssetTransactionComponentsPage, AssetTransactionDeleteDialog, AssetTransactionUpdatePage } from './asset-transaction.page-object';

const expect = chai.expect;

describe('AssetTransaction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let assetTransactionUpdatePage: AssetTransactionUpdatePage;
  let assetTransactionComponentsPage: AssetTransactionComponentsPage;
  let assetTransactionDeleteDialog: AssetTransactionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AssetTransactions', async () => {
    await navBarPage.goToEntity('asset-transaction');
    assetTransactionComponentsPage = new AssetTransactionComponentsPage();
    await browser.wait(ec.visibilityOf(assetTransactionComponentsPage.title), 5000);
    expect(await assetTransactionComponentsPage.getTitle()).to.eq('Asset Transactions');
  });

  it('should load create AssetTransaction page', async () => {
    await assetTransactionComponentsPage.clickOnCreateButton();
    assetTransactionUpdatePage = new AssetTransactionUpdatePage();
    expect(await assetTransactionUpdatePage.getPageTitle()).to.eq('Create or edit a Asset Transaction');
    await assetTransactionUpdatePage.cancel();
  });

  it('should create and save AssetTransactions', async () => {
    const nbButtonsBeforeCreate = await assetTransactionComponentsPage.countDeleteButtons();

    await assetTransactionComponentsPage.clickOnCreateButton();
    await promise.all([
      assetTransactionUpdatePage.setTransactionReferenceInput('transactionReference'),
      assetTransactionUpdatePage.setTransactionDateInput('2000-12-31'),
      assetTransactionUpdatePage.setScannedDocumentIdInput('5'),
      assetTransactionUpdatePage.setTransactionApprovalIdInput('5')
    ]);
    expect(await assetTransactionUpdatePage.getTransactionReferenceInput()).to.eq(
      'transactionReference',
      'Expected TransactionReference value to be equals to transactionReference'
    );
    expect(await assetTransactionUpdatePage.getTransactionDateInput()).to.eq(
      '2000-12-31',
      'Expected transactionDate value to be equals to 2000-12-31'
    );
    expect(await assetTransactionUpdatePage.getScannedDocumentIdInput()).to.eq('5', 'Expected scannedDocumentId value to be equals to 5');
    expect(await assetTransactionUpdatePage.getTransactionApprovalIdInput()).to.eq(
      '5',
      'Expected transactionApprovalId value to be equals to 5'
    );
    await assetTransactionUpdatePage.save();
    expect(await assetTransactionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await assetTransactionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AssetTransaction', async () => {
    const nbButtonsBeforeDelete = await assetTransactionComponentsPage.countDeleteButtons();
    await assetTransactionComponentsPage.clickOnLastDeleteButton();

    assetTransactionDeleteDialog = new AssetTransactionDeleteDialog();
    expect(await assetTransactionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Asset Transaction?');
    await assetTransactionDeleteDialog.clickOnConfirmButton();

    expect(await assetTransactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
