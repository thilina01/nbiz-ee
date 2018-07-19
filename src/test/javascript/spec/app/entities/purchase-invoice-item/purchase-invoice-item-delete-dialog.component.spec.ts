/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NBizEeTestModule } from '../../../test.module';
import { PurchaseInvoiceItemDeleteDialogComponent } from 'app/entities/purchase-invoice-item/purchase-invoice-item-delete-dialog.component';
import { PurchaseInvoiceItemService } from 'app/entities/purchase-invoice-item/purchase-invoice-item.service';

describe('Component Tests', () => {
    describe('PurchaseInvoiceItem Management Delete Component', () => {
        let comp: PurchaseInvoiceItemDeleteDialogComponent;
        let fixture: ComponentFixture<PurchaseInvoiceItemDeleteDialogComponent>;
        let service: PurchaseInvoiceItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [PurchaseInvoiceItemDeleteDialogComponent]
            })
                .overrideTemplate(PurchaseInvoiceItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseInvoiceItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseInvoiceItemService);
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
