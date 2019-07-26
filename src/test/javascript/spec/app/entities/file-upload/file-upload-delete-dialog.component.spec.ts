/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { FileUploadDeleteDialogComponent } from 'app/entities/file-upload/file-upload-delete-dialog.component';
import { FileUploadService } from 'app/entities/file-upload/file-upload.service';

describe('Component Tests', () => {
  describe('FileUpload Management Delete Component', () => {
    let comp: FileUploadDeleteDialogComponent;
    let fixture: ComponentFixture<FileUploadDeleteDialogComponent>;
    let service: FileUploadService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FileUploadDeleteDialogComponent]
      })
        .overrideTemplate(FileUploadDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileUploadDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileUploadService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
