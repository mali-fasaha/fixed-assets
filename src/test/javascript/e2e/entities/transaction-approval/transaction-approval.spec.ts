/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TransactionApprovalComponentsPage,
  TransactionApprovalDeleteDialog,
  TransactionApprovalUpdatePage
} from './transaction-approval.page-object';

const expect = chai.expect;

describe('TransactionApproval e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionApprovalUpdatePage: TransactionApprovalUpdatePage;
  let transactionApprovalComponentsPage: TransactionApprovalComponentsPage;
  let transactionApprovalDeleteDialog: TransactionApprovalDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TransactionApprovals', async () => {
    await navBarPage.goToEntity('transaction-approval');
    transactionApprovalComponentsPage = new TransactionApprovalComponentsPage();
    await browser.wait(ec.visibilityOf(transactionApprovalComponentsPage.title), 5000);
    expect(await transactionApprovalComponentsPage.getTitle()).to.eq('Transaction Approvals');
  });

  it('should load create TransactionApproval page', async () => {
    await transactionApprovalComponentsPage.clickOnCreateButton();
    transactionApprovalUpdatePage = new TransactionApprovalUpdatePage();
    expect(await transactionApprovalUpdatePage.getPageTitle()).to.eq('Create or edit a Transaction Approval');
    await transactionApprovalUpdatePage.cancel();
  });

  it('should create and save TransactionApprovals', async () => {
    const nbButtonsBeforeCreate = await transactionApprovalComponentsPage.countDeleteButtons();

    await transactionApprovalComponentsPage.clickOnCreateButton();
    await promise.all([
      transactionApprovalUpdatePage.setDescriptionInput('description'),
      transactionApprovalUpdatePage.setRequestedByInput('5'),
      transactionApprovalUpdatePage.setRecommendedByInput('recommendedBy'),
      transactionApprovalUpdatePage.setReviewedByInput('reviewedBy'),
      transactionApprovalUpdatePage.setFirstApproverInput('firstApprover'),
      transactionApprovalUpdatePage.setSecondApproverInput('secondApprover'),
      transactionApprovalUpdatePage.setThirdApproverInput('thirdApprover'),
      transactionApprovalUpdatePage.setFourthApproverInput('fourthApprover')
    ]);
    expect(await transactionApprovalUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await transactionApprovalUpdatePage.getRequestedByInput()).to.eq('5', 'Expected requestedBy value to be equals to 5');
    expect(await transactionApprovalUpdatePage.getRecommendedByInput()).to.eq(
      'recommendedBy',
      'Expected RecommendedBy value to be equals to recommendedBy'
    );
    expect(await transactionApprovalUpdatePage.getReviewedByInput()).to.eq(
      'reviewedBy',
      'Expected ReviewedBy value to be equals to reviewedBy'
    );
    expect(await transactionApprovalUpdatePage.getFirstApproverInput()).to.eq(
      'firstApprover',
      'Expected FirstApprover value to be equals to firstApprover'
    );
    expect(await transactionApprovalUpdatePage.getSecondApproverInput()).to.eq(
      'secondApprover',
      'Expected SecondApprover value to be equals to secondApprover'
    );
    expect(await transactionApprovalUpdatePage.getThirdApproverInput()).to.eq(
      'thirdApprover',
      'Expected ThirdApprover value to be equals to thirdApprover'
    );
    expect(await transactionApprovalUpdatePage.getFourthApproverInput()).to.eq(
      'fourthApprover',
      'Expected FourthApprover value to be equals to fourthApprover'
    );
    await transactionApprovalUpdatePage.save();
    expect(await transactionApprovalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionApprovalComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TransactionApproval', async () => {
    const nbButtonsBeforeDelete = await transactionApprovalComponentsPage.countDeleteButtons();
    await transactionApprovalComponentsPage.clickOnLastDeleteButton();

    transactionApprovalDeleteDialog = new TransactionApprovalDeleteDialog();
    expect(await transactionApprovalDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Transaction Approval?');
    await transactionApprovalDeleteDialog.clickOnConfirmButton();

    expect(await transactionApprovalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
