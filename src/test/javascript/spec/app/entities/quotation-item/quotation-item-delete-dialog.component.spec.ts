/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NBizEeTestModule } from '../../../test.module';
import { QuotationItemDeleteDialogComponent } from 'app/entities/quotation-item/quotation-item-delete-dialog.component';
import { QuotationItemService } from 'app/entities/quotation-item/quotation-item.service';

describe('Component Tests', () => {
    describe('QuotationItem Management Delete Component', () => {
        let comp: QuotationItemDeleteDialogComponent;
        let fixture: ComponentFixture<QuotationItemDeleteDialogComponent>;
        let service: QuotationItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [QuotationItemDeleteDialogComponent]
            })
                .overrideTemplate(QuotationItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuotationItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuotationItemService);
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
