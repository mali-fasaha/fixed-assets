/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AssetAcquisitionComponentsPage, AssetAcquisitionDeleteDialog, AssetAcquisitionUpdatePage } from './asset-acquisition.page-object';

const expect = chai.expect;

describe('AssetAcquisition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let assetAcquisitionUpdatePage: AssetAcquisitionUpdatePage;
  let assetAcquisitionComponentsPage: AssetAcquisitionComponentsPage;
  let assetAcquisitionDeleteDialog: AssetAcquisitionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AssetAcquisitions', async () => {
    await navBarPage.goToEntity('asset-acquisition');
    assetAcquisitionComponentsPage = new AssetAcquisitionComponentsPage();
    await browser.wait(ec.visibilityOf(assetAcquisitionComponentsPage.title), 5000);
    expect(await assetAcquisitionComponentsPage.getTitle()).to.eq('Asset Acquisitions');
  });

  it('should load create AssetAcquisition page', async () => {
    await assetAcquisitionComponentsPage.clickOnCreateButton();
    assetAcquisitionUpdatePage = new AssetAcquisitionUpdatePage();
    expect(await assetAcquisitionUpdatePage.getPageTitle()).to.eq('Create or edit a Asset Acquisition');
    await assetAcquisitionUpdatePage.cancel();
  });

  it('should create and save AssetAcquisitions', async () => {
    const nbButtonsBeforeCreate = await assetAcquisitionComponentsPage.countDeleteButtons();

    await assetAcquisitionComponentsPage.clickOnCreateButton();
    await promise.all([
      assetAcquisitionUpdatePage.setDescriptionInput('description'),
      assetAcquisitionUpdatePage.setAcquisitionMonthInput('2000-12-31'),
      assetAcquisitionUpdatePage.setAssetSerialInput('assetSerial'),
      assetAcquisitionUpdatePage.setServiceOutletCodeInput('serviceOutletCode'),
      assetAcquisitionUpdatePage.setAcquisitionTransactionIdInput('5'),
      assetAcquisitionUpdatePage.setAssetCategoryIdInput('5'),
      assetAcquisitionUpdatePage.setPurchaseAmountInput('5'),
      assetAcquisitionUpdatePage.setAssetDealerIdInput('5'),
      assetAcquisitionUpdatePage.setAssetInvoiceIdInput('5')
    ]);
    expect(await assetAcquisitionUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await assetAcquisitionUpdatePage.getAcquisitionMonthInput()).to.eq(
      '2000-12-31',
      'Expected acquisitionMonth value to be equals to 2000-12-31'
    );
    expect(await assetAcquisitionUpdatePage.getAssetSerialInput()).to.eq(
      'assetSerial',
      'Expected AssetSerial value to be equals to assetSerial'
    );
    expect(await assetAcquisitionUpdatePage.getServiceOutletCodeInput()).to.eq(
      'serviceOutletCode',
      'Expected ServiceOutletCode value to be equals to serviceOutletCode'
    );
    expect(await assetAcquisitionUpdatePage.getAcquisitionTransactionIdInput()).to.eq(
      '5',
      'Expected acquisitionTransactionId value to be equals to 5'
    );
    expect(await assetAcquisitionUpdatePage.getAssetCategoryIdInput()).to.eq('5', 'Expected assetCategoryId value to be equals to 5');
    expect(await assetAcquisitionUpdatePage.getPurchaseAmountInput()).to.eq('5', 'Expected purchaseAmount value to be equals to 5');
    expect(await assetAcquisitionUpdatePage.getAssetDealerIdInput()).to.eq('5', 'Expected assetDealerId value to be equals to 5');
    expect(await assetAcquisitionUpdatePage.getAssetInvoiceIdInput()).to.eq('5', 'Expected assetInvoiceId value to be equals to 5');
    await assetAcquisitionUpdatePage.save();
    expect(await assetAcquisitionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await assetAcquisitionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AssetAcquisition', async () => {
    const nbButtonsBeforeDelete = await assetAcquisitionComponentsPage.countDeleteButtons();
    await assetAcquisitionComponentsPage.clickOnLastDeleteButton();

    assetAcquisitionDeleteDialog = new AssetAcquisitionDeleteDialog();
    expect(await assetAcquisitionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Asset Acquisition?');
    await assetAcquisitionDeleteDialog.clickOnConfirmButton();

    expect(await assetAcquisitionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
