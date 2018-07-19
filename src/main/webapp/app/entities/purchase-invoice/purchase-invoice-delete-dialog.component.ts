import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseInvoice } from 'app/shared/model/purchase-invoice.model';
import { PurchaseInvoiceService } from './purchase-invoice.service';

@Component({
    selector: 'jhi-purchase-invoice-delete-dialog',
    templateUrl: './purchase-invoice-delete-dialog.component.html'
})
export class PurchaseInvoiceDeleteDialogComponent {
    purchaseInvoice: IPurchaseInvoice;

    constructor(
        private purchaseInvoiceService: PurchaseInvoiceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchaseInvoiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchaseInvoiceListModification',
                content: 'Deleted an purchaseInvoice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-invoice-delete-popup',
    template: ''
})
export class PurchaseInvoiceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseInvoice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchaseInvoiceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.purchaseInvoice = purchaseInvoice;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
