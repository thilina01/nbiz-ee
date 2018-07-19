import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';
import { PurchaseInvoiceItemService } from './purchase-invoice-item.service';

@Component({
    selector: 'jhi-purchase-invoice-item-delete-dialog',
    templateUrl: './purchase-invoice-item-delete-dialog.component.html'
})
export class PurchaseInvoiceItemDeleteDialogComponent {
    purchaseInvoiceItem: IPurchaseInvoiceItem;

    constructor(
        private purchaseInvoiceItemService: PurchaseInvoiceItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchaseInvoiceItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchaseInvoiceItemListModification',
                content: 'Deleted an purchaseInvoiceItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-invoice-item-delete-popup',
    template: ''
})
export class PurchaseInvoiceItemDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseInvoiceItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchaseInvoiceItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.purchaseInvoiceItem = purchaseInvoiceItem;
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
