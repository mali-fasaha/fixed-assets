/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FileUploadDetailComponent } from 'app/entities/file-upload/file-upload-detail.component';
import { FileUpload } from 'app/shared/model/file-upload.model';

describe('Component Tests', () => {
  describe('FileUpload Management Detail Component', () => {
    let comp: FileUploadDetailComponent;
    let fixture: ComponentFixture<FileUploadDetailComponent>;
    const route = ({ data: of({ fileUpload: new FileUpload(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FileUploadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FileUploadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileUploadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fileUpload).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
